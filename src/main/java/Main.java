import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Main {

	public static void main(String[] args) {

		
		//Create a SparkContext to initialize
        //SparkConf conf2 = new SparkConf().setMaster("local").setAppName("Word Count");

        // Create a Java version of the Spark Context
        //SparkContext sc = new SparkContext(conf2);
		
        
        
        SparkSession ss = SparkSession.builder()
						        		.master("local")
						        		.appName("teste")
						        		.enableHiveSupport()
						        		.getOrCreate();

		// Create a SparkContext to initialize
		// SparkConf configure = new
		// SparkConf().setMaster("yarn-client").setAppName("IngestJavaWithHBase");
		//SparkConf configure = new SparkConf(false).setMaster("192.168.15.28").setAppName("IngestJavaWithHBase");


        
		// Create a Java version of the Spark Context
		//JavaSparkContext sc = new JavaSparkContext(configure);
		// SparkContext sc2 = new SparkContext();
		// JavaSQLContext sqlContext = new JavaSQLContext(sc);
        //HiveContext hctx = new HiveContext(sc);

		// SparkSession spark = SparkSession.builder().appName("Java Spark Hive
		// Example").enableHiveSupport().getOrCreate();

		try {

			String jdbcSqlConnStr = "jdbc:sqlserver://ULTRACASTRO-PC;database=db_stg_casasbahia;user=sa;password=daniel1001";
			String tableQuery = "(select * from Compra cp where cp.Data >= DATEADD(year,-1,GETDATE())) as Compras";
			//Dataset<Row> ds  = hctx.read().format("jdbc").option("url", jdbcSqlConnStr).option("dbtable", tableQuery).load();
			Properties p = new Properties();
			
			//set new runtime options
			ss.conf().set("spark.sql.shuffle.partitions", 6);
			ss.conf().set("spark.executor.memory", "2g");
					
			Dataset<Row> ds  = ss.read().jdbc(jdbcSqlConnStr, tableQuery, p);
			
			
			ds.write().orc("//teste");
			System.out.println(ds);
			/**
			Configuration conf = HBaseConfiguration.create();

			// Passar o IP zookeper server
			conf.set("hbase.zookeeper.quorum", "10.128.132.221");

			conf.set("hbase.client.retries.number", Integer.toString(1));
			conf.set("zookeeper.session.timeout", Integer.toString(60000));
			conf.set("zookeeper.recovery.retry", Integer.toString(1));
			conf.set("zookeeper.znode.parent", "/hbase-unsecure");
			conf.set("hbase.zookeeper.property.clientport", "2181");
			Connection connection = ConnectionFactory.createConnection(conf);

			Result result = new Result();

			// Connection connection = ConnectionFactory.createConnection(conf);
			Table table = connection.getTable(TableName.valueOf(Bytes.toBytes("ingestion:properties")));
			Get get = new Get(Bytes.toBytes("compra"));
			get.addFamily(Bytes.toBytes("config"));
			result = table.get(get);
			byte[] value = result.getValue("config".getBytes(), "banco".getBytes());
			String s = new String(value);
			System.out.println(s);

			// Dataset<Row> jdbcDF = spark.read()
			// .format("jdbc")
			// .option("url", jdbcSqlConnStr)
			// .option("dbtable", tableQuery)
			// .load();
			**/

		} catch (Exception exp) {

			System.out.println("" + exp.getMessage());
		}

		// Load the text into a Spark RDD, which is a distributed representation
		// of each line of text
		/**
		 * JavaRDD<String> textFile =
		 * sc.textFile("src/main/resources/shakespeare.txt");
		 * JavaPairRDD<String, Integer> counts = textFile .flatMap(s ->
		 * Arrays.asList(s.split("[ ,]")).iterator()) .mapToPair(word -> new
		 * Tuple2<>(word, 1)) .reduceByKey((a, b) -> a + b); counts.foreach(p ->
		 * System.out.println(p)); System.out.println("Total words: " +
		 * counts.count()); counts.saveAsTextFile("/tmp/shakespeareWordCount");
		 **/
	}

}
