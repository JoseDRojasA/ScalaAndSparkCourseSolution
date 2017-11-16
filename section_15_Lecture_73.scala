import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{PCA, VectorAssembler, StandardScaler}
import org.apache.spark.ml.linalg.Vectors

val spark = SparkSession.builder().appName("PCA_Example").getOrCreate()

val data = spark.read.option("header", "true").option("inferSchema", "true").format("csv").load("data/Cancer_Data")

data.printSchema()

val assembler = new VectorAssembler().setInputCols(
                    Array(
                        "mean radius",
                        "mean texture",
                        "mean perimeter",
                        "mean area",
                        "mean smoothness",
                        "mean compactness",
                        "mean concavity",
                        "mean concave points",
                        "mean symmetry",
                        "mean fractal dimension",
                        "radius error",
                        "texture error",
                        "perimeter error",
                        "area error",
                        "smoothness error",
                        "compactness error",
                        "concavity error",
                        "concave points error",
                        "symmetry error",
                        "fractal dimension error",
                        "worst radius",
                        "worst texture",
                        "worst perimeter",
                        "worst area",
                        "worst smoothness",
                        "worst compactness",
                        "worst concavity",
                        "worst concave points",
                        "worst symmetry",
                        "worst fractal dimension"
                    )
                ).setOutputCol("features")
val features = assembler.transform(data).select($"features")
val scaler = new StandardScaler().setInputCol("features").setOutputCol("scaledFeatures").setWithStd(true).setWithMean(false)

val scalerModel = scaler.fit(features)

val scaledData = scalerModel.transform(features)

val pca = new PCA().setInputCol("scaledFeatures").setOutputCol("pcaFeatures").setK(4).fit(scaledData)

val pcaDF = pca.transform(scaledData)

val results = pcaDF.select("pcaFeatures")
results.show()

results.head(1)