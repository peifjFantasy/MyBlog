package com.blog.lucene;

import com.blog.entity.Blog;
import com.blog.util.DateUtil;
import com.blog.util.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Service;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: john
 * @Date: 2019/9/18 22:15
 * @Description: 使用lucene对博客索引进行CRUD
 * @version: 1.0
 */
public class BlogIndex {

    private Directory directory=null;
    private String lucentPath="D://LuceneIndexRep";


    /**
     * 功能描述：获取IndexWriter对象
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    private IndexWriter getWriter() throws IOException {
        directory= FSDirectory.open(Paths.get(lucentPath,new String[0]));
        //获取一个支持中文的分析器
        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
        IndexWriter indexWriter=new IndexWriter(directory,new IndexWriterConfig(analyzer));
        return indexWriter;
    }

    /**
     * 功能描述：向索引库中增加博客索引
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    public void addIndexToBlog(Blog blog) throws IOException {
        IndexWriter indexWriter=getWriter();
        //获取文档对象
        Document doc=new Document();
        //给文档对象添加检索域对象（把一个文档分割为不同的域再去检索）
        doc.add(new StringField("id",String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title",blog.getTitle(), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(),"yyyy-MM-dd"), Field.Store.YES));
        doc.add(new TextField("content",blog.getContentNoTag(), Field.Store.YES));
        doc.add(new TextField("keyWord",blog.getKeyWord(), Field.Store.YES));
        //使用IndexWriter把该文档对象写入到本地索引库
        indexWriter.addDocument(doc);
        indexWriter.close();
    }

    /**
     * 功能描述：修改索引库中博客对应的索引
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    public void updIndexToBlog(Blog blog) throws IOException {
        IndexWriter indexWriter=getWriter();
        Document doc=new Document();
        doc.add(new StringField("id",String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title",blog.getTitle(), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(),"yyyy-MM-dd"), Field.Store.YES));
        doc.add(new TextField("content",blog.getContentNoTag(), Field.Store.YES));
        doc.add(new TextField("keyWord",blog.getKeyWord(), Field.Store.YES));
        indexWriter.updateDocument(new Term("id",String.valueOf(blog.getId())),doc);
        indexWriter.close();
    }

    /**
     * 功能描述：删除索引库中博客对应的索引
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    public void delIndexToBlog(String blogId) throws IOException {
        IndexWriter indexWriter=getWriter();
        indexWriter.deleteDocuments(new Term[]{new Term("id",blogId)});
        indexWriter.forceMergeDeletes();
        indexWriter.commit();
        indexWriter.close();
    }

    /**
     * 功能描述：检索索引库中的索引
     * @param:
     * @return:
     * @author: john
     * @date:
     */
    public List<Blog> searchBlog(String q) throws Exception {
        List<Blog> blogList=new LinkedList<>();
        directory= FSDirectory.open(Paths.get(lucentPath,new String[0]));
        //获取IndexReader
        IndexReader indexReader= DirectoryReader.open(directory);
        //获取IndexSearcher
        IndexSearcher searcher=new IndexSearcher(indexReader);
        //放入查询条件
        BooleanQuery.Builder builder=new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
        QueryParser parser=new QueryParser("title",analyzer);
        Query query = parser.parse(q);
        QueryParser parser2=new QueryParser("content",analyzer);
        Query query2 = parser2.parse(q);
        QueryParser parser3=new QueryParser("keyWord",analyzer);
        Query query3 = parser3.parse(q);

        builder.add(query, BooleanClause.Occur.SHOULD);
        builder.add(query2, BooleanClause.Occur.SHOULD);
        builder.add(query3, BooleanClause.Occur.SHOULD);

        //最多返回100条数据
        TopDocs hits= searcher.search(builder.build(),100);

        //高亮显示搜索字
        QueryScorer scorer=new QueryScorer(query);
        Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter,scorer);
        highlighter.setTextFragmenter(fragmenter);

        //遍历查询结果，放入list
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc=searcher.doc(scoreDoc.doc);
            Blog blog=new Blog();
            blog.setId(Integer.valueOf(doc.get("id")));
            blog.setReleaseDateStr(doc.get("releaseDate"));
            String title=doc.get("title");
            String content= StringEscapeUtils.escapeHtml(doc.get("content"));
            String keyWord=doc.get("ketWord");

            if(title!=null){
                TokenStream tokenStream=analyzer.tokenStream("title",new StringReader(title));
                String hTitle=highlighter.getBestFragment(tokenStream,title);
                if(StringUtil.isNotEmpty(hTitle)){
                    blog.setTitle(hTitle);
                }else {
                    blog.setTitle(title);
                }
            }

            if(content!=null){
                TokenStream tokenStream=analyzer.tokenStream("content",new StringReader(content));
                String hContent=highlighter.getBestFragment(tokenStream,content);
                if(StringUtil.isNotEmpty(hContent)){
                    blog.setContent(hContent);
                }else {
                    if(content.length() <= 200){
                        blog.setContent(content);
                    }else {
                        blog.setContent(content.substring(0,200));
                    }
                }
            }

            if(keyWord!=null){
                TokenStream tokenStream=analyzer.tokenStream("keyWord",new StringReader(keyWord));
                String hKeyWord=highlighter.getBestFragment(tokenStream,keyWord);
                if(StringUtil.isNotEmpty(hKeyWord)){
                    blog.setKeyWord(hKeyWord);
                }else {
                    blog.setKeyWord(keyWord);
                }
            }
            blogList.add(blog);
        }
        return blogList;
    }



}
