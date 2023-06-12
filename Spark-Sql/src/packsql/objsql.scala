package packsql
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Row
import org.apache.spark.sql.types._
object objsql {
	def main(args:Array[String]):Unit={ 
			val conf = new SparkConf().setAppName("first").setMaster("local[*]")
					val sc = new SparkContext(conf)
					sc.setLogLevel("ERROR")
					val spark = SparkSession.builder().getOrCreate()
					import spark.implicits._

					val df = spark.read.option("header","true").csv("file:///E:/data/df.csv")

					val df1 = spark.read.option("header","true").csv("file:///E:/data/df1.csv")

					val cust = spark.read.option("header","true").csv("file:///E:/data/cust.csv")

					val prod = spark.read.option("header","true").csv("file:///E:/data/prod.csv")

					df.show()
					df1.show()
					cust.show()
					prod.show()


					df.createOrReplaceTempView("df")
					df1.createOrReplaceTempView("df1")
					cust.createOrReplaceTempView("cust")
					prod.createOrReplaceTempView("prod")]
		
					println("=====df=====")
					spark.sql("select * from df order by id").show()
					println("=====df1=====")
					spark.sql("select * from df1  order by id").show()

					
					println("=====Validate Data=====")
										

										
					spark.sql("select * from df  ").show()
										
					spark.sql("select id,tdate from df  order by id").show()

										
					spark.sql("select id,tdate,category from df where category='Exercise' order by id").show()
										
													
					spark.sql("select id,tdate,category,spendby from df where category='Exercise' and spendby='cash' ").show()

					
					spark.sql("select * from df where category in ('Exercise','Gymnastics')").show()


					spark.sql("select * from df where product like ('%Gymnastics%')").show()

					
					spark.sql("select * from df   where category != 'Exercise'").show()

					
					spark.sql("select * from df where category not in ('Exercise','Gymnastics')").show()
					
					spark.sql("select * from df where product is null").show()
					
					spark.sql("select * from df where product is not null").show()


					spark.sql("select max(id) from df").show()
					
					spark.sql("select min(id) from df").show()
					
					spark.sql("select count(1) from df").show()
				
										
					spark.sql("select *,case when spendby='cash' then 1 else 0 end  as status from df").show()

										
					spark.sql("select id,category,concat(id,'-',category) as condata from df").show()


					
					spark.sql("select id,category,product,concat_ws('-',id,category,product) as condata from df").show()  
					
					
										
					spark.sql("select category,lower(category) as lower  from df ").show()

					spark.sql("select amount,ceil(amount) as ceil from df").show()
					
					
					spark.sql("select amount,round(amount) as round from df").show()
					


					spark.sql("select product,coalesce(product,'NA') as nullrep from df").show()
										
										
					spark.sql("select trim(product) from df").show()
										
										
					spark.sql("select distinct category,spendby from df").show()
										




					spark.sql("select substring(product,1,10) as sub from df").show()
					
					spark.sql("select SUBSTRING_INDEX(category,' ',1) as spl from df").show()
					
					spark.sql("select * from df union all select * from df1").show()
										
					spark.sql("select * from df union select * from df1 order by id").show()


										
					spark.sql("select category, sum(amount) as total from df group by category").show()
										
					spark.sql("select category, spendby,sum(amount)  as total from df group by category,spendby").show()
										
					spark.sql("select category, spendby,sum(amount) As total,count(amount)  as cnt from df group by category,spendby").show()
										
					spark.sql("select category, max(amount) as max from df group by category").show()
										
					spark.sql("select category, max(amount) as max from df group by category order by category desc").show()



					
					
					spark.sql("SELECT category,amount, row_number() OVER ( partition by category order by amount desc ) AS row_number FROM df").show()
					
					spark.sql("SELECT category,amount, dense_rank() OVER ( partition by category order by amount desc ) AS dense_rank FROM df").show()
					
					
					spark.sql("SELECT category,amount, rank() OVER ( partition by category order by amount desc ) AS rank FROM df").show()
									
					spark.sql("SELECT category,amount, lead(amount) OVER ( partition by category order by amount desc ) AS lead FROM df").show()
					
					
					spark.sql("SELECT category,amount, lag(amount) OVER ( partition by category order by amount desc ) AS lag FROM df").show()

					
					
					spark.sql("select category,count(category) as cnt from df group by category having count(category)>1").show()
					
					spark.sql("select a.id,a.name,b.product from cust a join prod b on a.id=b.id").show()
					
					
					spark.sql("select a.id,a.name,b.product from cust a left join prod b on a.id=b.id").show()
					
					
					spark.sql("select a.id,a.name,b.product from cust a right join prod b on a.id=b.id").show()
					
					
					spark.sql("select a.id,a.name,b.product from cust a full join prod b on a.id=b.id").show()




					spark.sql("select a.id,a.name from cust a LEFT ANTI JOIN  prod b on a.id=b.id").show()


					spark.sql("select id,tdate,from_unixtime(unix_timestamp(tdate,'MM-dd-yyyy'),'yyyy-MM-dd') as con_date from df").show()

					spark.sql("""

							select sum(amount) as total , con_date from(

							select id,tdate,from_unixtime(unix_timestamp(tdate,'MM-dd-yyyy'),'yyyy-MM-dd') as con_date,amount,category,product,spendby from df) 

							group by con_date

							""").show()



	}

}

