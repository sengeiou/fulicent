package com.fulicent.api.dao;

import com.fulicent.api.entity.Products;
import com.fulicent.common.entity.LuceneDoc;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductsDao {
	 @Select("<script> "+
	    "SELECT " +
	    "t.* " +
	    "FROM " +
	    " fulicent.products t " +
	    "WHERE " +
	    " t.Status=1 " +
	    "<if test='categoryId!=\"\" and categoryId!=null'>" +
	    " and t.CategoryId= ${categoryId} " +
	    "</if>  " +	    
	    "<if test='brand!=\"\" and brand!=null'>" +
	    " and t.Brand= '${brand}' " +
	    "</if>  " +	    
	    "<if test='recommend!=\"\" and recommend!=null'>" +
	    " and t.Recommend> ${recommend} " +
	    "</if>  " +	
	    "<if test='tag!=\"\" and tag!=null'>" +
	    " and t.tag like CONCAT('%',#{tag},'%') " +
	    "</if>  " +	 
	    "<if test='sort == \"Name\"'>  " +
	    " ORDER BY t.Name ${order} " +
	    "</if>  " +
	    "<if test='sort == \"CreateTime\"'>  " +
	    " ORDER BY t.CreateTime  ${order} " +
	    "</if>  " +	   
	    "<if test='sort == \"Recommend\"'>  " +
	    " ORDER BY t.Recommend  ${order} " +
	    "</if>  " +
	    "<if test ='limit gt 0'>" +
	    "LIMIT #{limit} OFFSET #{skip}</if>" +
	    "</script>")
	List<Products> Products(@Param("limit") int limit, @Param("skip") int skip, @Param("sort") String sort, @Param("order") String order, @Param("categoryId") String categoryId, @Param("recommend") String recommend, @Param("brand") String brand, @Param("tag") String tag);

	 @Select("<script>SELECT A.*,B.Name as CategoryId FROM fulicent.products A, fulicent.category B where A.CategoryId=B.Id and A.id=${id};"
	 		+ "insert into fulicent.topproducts(productId,Count) value(${id},1) on duplicate key update Count=Count+1;</script>")
	 Products Product(@Param("id") int id);
	 
	 @Select("<script>select t.* from fulicent.topproducts A, fulicent.products t where t.Type=0 and t.Status=1 and A.ProductId=t.Id"+
			    "<if test='categoryId!=\"\" and categoryId!=null'>" +
			    " and t.CategoryId= ${categoryId} " +
			    "</if>  " +	
			    " order by A.count desc"+
			    "<if test ='limit gt 0'>" +
			    "LIMIT #{limit} OFFSET #{skip}</if>" +
			    "</script>")
	 List<Products> TopProducts(@Param("limit") int limit, @Param("skip") int skip, @Param("categoryId") String categoryId);
	 
	 @Select("<script>select count(1) from fulicent.topproducts A, fulicent.products t where t.Type=0 and t.Status=1 and A.ProductId=t.Id"+
			    "<if test='categoryId!=\"\" and categoryId!=null'>" +
			    " and t.CategoryId= ${categoryId} " +
			    "</if>  " +
			    "</script>")
	 int CountTopProducts( @Param("categoryId") String categoryId);
	 
	 @Select("<script> "+
			    " SELECT " +
			    " t.* " +
			    " FROM " +
			    " fulicent.products t " +
			    " WHERE " +
			    " t.Type in(1,2,3)"+ 
			    " And t.Status=1 " +
			    " order by Id desc"+
			    "<if test ='limit gt 0'>" +
			    " LIMIT #{limit} OFFSET #{skip}</if>" +
			    "</script>")
	 List<Products> AdProducts(@Param("limit") int limit, @Param("skip") int skip);
	 
	 @Select("<script> "+
			    "SELECT " +
			    "t.* " +
			    "FROM " +
			    " fulicent.products t " +
			    "WHERE " +
			    " t.Status=1 " +
			    "<if test='ids.length>0 and ids!=null'>" +
			    " and t.Id in" + 
			    " <foreach collection='ids' item='id' open='(' separator=',' close=')'> #{id} </foreach>"+
			    " order by field(Id, <foreach collection='ids' item='id' separator=',' > #{id} </foreach> )"+
			    "</if>  " +	
			    "<if test ='limit gt 0'>" +
			    "LIMIT #{limit} OFFSET #{skip}</if>" +
			    "</script>")
			List<Products> MyProducts(@Param("limit") int limit, @Param("skip") int skip, @Param("ids") String[] ids);

	 @Select("<script> "+
			    "SELECT " +
			    "count(1) " +
			    "FROM " +
			    " fulicent.products t " +
			    "WHERE " +
			    " t.Status=1 " +
			    "<if test='categoryId!=\"\" and categoryId!=null'>" +
			    " and t.CategoryId= ${categoryId} " +
			    "</if>  " +	    
			    "<if test='brand!=\"\" and brand!=null'>" +
			    " and t.Brand= '${brand}' " +
			    "</if>  " +	    
			    "<if test='recommend!=\"\" and recommend!=null'>" +
			    " and t.Recommend> ${recommend} " +
			    "</if>  " +	
			    "<if test='ids.length>0 and ids!=null'>" +
			    " and t.Id in" + 
			    " <foreach collection='ids' item='id' open='(' separator=',' close=')'> #{id} </foreach>"+
			    " order by field(Id, <foreach collection='ids' item='id' separator=',' > #{id} </foreach> )"+
			    "</if>  " +	
			    "</script>")
			int Count( @Param("categoryId") String categoryId, @Param("recommend") String recommend, @Param("brand") String brand, @Param("ids") String[] ids);
	
	 
	 @Update("UPDATE"+ 
	 		" fulicent.products"+ 
	 		" SET `Name`=#{name},"+
	 		" `Links`=#{links},"+
	 		" `Content`=#{content},"+
	 		" `Image`=#{image},"+
	 		" `CouponPrice`=#{couponPrice},"+
	 		" `Coupon`=#{coupon},"+
	 		" `ReservePrice`=#{reservePrice},"+
	 		" `Anticipation`=#{anticipation},"+
	 		" `Status`=#{status},"+
	 		" `Type`=#{type},"+
	 		" `Expire`=#{expire},"+
	 		" `Sale`=#{sale},"+
	 		" `CouponRemainCount`=#{couponRemainCount},"+
	 		" `CategoryId`=#{categoryId},"+
	 		" `Brand`=#{brand},"+
	 		" `SmallImages`=#{smallImages},"+
	 		" `Recommend`=#{recommend},"+
	 		" `Tag`=#{tag},"+
	 		" `LastUpdate`=CURRENT_TIMESTAMP"+
	 		" WHERE " +
	 		" `Id`=#{id}")
	 int UpdateProduct(Products product);
	 
	 
	@Insert("INSERT INTO "+
			" fulicent.products"+
			" ("+
			" `Name`,"+
			" `NumIID`,"+
			" `Links`,"+
			" `Content`,"+
			" `Image`,"+
			" `SmallImages`,"+
			" `CouponPrice`,"+
			" `Coupon`,"+
			" `ReservePrice`,"+
			" `Anticipation`,"+
			" `Status`,"+
			" `Type`,"+
			" `CreateTime`,"+
			" `Expire`,"+
			" `Sale`,"+
			" `CouponRemainCount`,"+
			" `CategoryId`,"+
			" `Brand`,"+
			" `Tag`,"+
			" `Recommend`"+
			" ) VALUES"+
			" ("+
			" #{name},"+
			" #{numIID},"+
			" #{links},"+
			" #{content},"+
			" #{image},"+
			" #{smallImages},"+
			" #{couponPrice},"+
			" #{coupon},"+
			" #{reservePrice},"+
			" #{anticipation},"+
			" #{status},"+
			" #{type},"+
			" CURRENT_TIMESTAMP,"+
			" #{expire},"+
			" #{sale},"+
			" #{couponRemainCount},"+
			" #{categoryId},"+
			" #{brand},"+
			" #{tag},"+
			" #{recommend}"+
	")")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() AS id", keyProperty = "id", before = false, resultType = int.class)
    int SaveProduct(Products product);
	 
	 @Select("<script> "+
			    " SELECT " +
			    " t.*" +
			    " FROM " +
			    " fulicent.products t " +
			    " WHERE " +
			    " t.Status=1 " +
			    "</script>")
	public List<LuceneDoc> GetLuceneDoc();
}
