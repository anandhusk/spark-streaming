package ask

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.StreamingQuery

object StreamingWC {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[3]")
      .appName("streamer")
      .config("spark.streaming.stopGracefullyOnShutdown", "true")
      .config("spark.sql.shuffle.partitions", 3)
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val lineDf = spark
      .readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", "9999")
      .load()

    val wordDf = lineDf
      .select(expr("explode(split(value,' ')) as word"))

    val countDf = wordDf.groupBy("word").count()

    val wordCountQuery: StreamingQuery = countDf
      .writeStream
      .format("console")
      .option("checkpointLocation", "chkpt-dr")
      .outputMode("complete")
      .start()

    wordCountQuery.awaitTermination()
  }

}
