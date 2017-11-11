import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema", "true").csv("data/ContainsNull.csv")

df.printSchema()
df.show()
// df.na.drop(2).show()
// df.na.fill(100).show()
// df.na.fill("100").show()
// df.na.fill("New Name", Array("Name")).show()
// df.na.fill(200, Array("Sales")).show()
// df.describe().show()
val df2 = df.na.fill(400.5, Array("Sales")).na.fill("missing name", Array("Name"))
df2.show()
