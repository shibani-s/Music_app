<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 $response["products"] = array();
// check for required fields
if (isset($_POST['Name']) && isset($_POST['Album']) && isset($_POST['Artist'])  ) {
 
    $Name = $_POST['Name'];
    $Album = $_POST['Album'];
    $Artist = $_POST['Artist'];
	 $Year = $_POST['Year'];
	 $task=$_POST["task"];
	$taskarray=array();
	$taskarray["sleeping"]="Romantic";
	$taskarray["cleaning"]="Instrumenatl";
	$taskarray["studying"]="Happy";
	$ftask=$taskarray[$task];
	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
	$result1 = mysql_query("SELECT Name,Artist,$ftask FROM songs") or die(mysql_error());
    // mysql inserting a new row
   
 
    // check if row inserted or not
    if ($result1) {
        // successfully inserted into database
		if (mysql_num_rows($result1) > 0) {
		
    // looping through all results
    // products node
    
    $response["success"]=0;
	$response["message"]="executing";
	$yes=0;
    while ($row = mysql_fetch_array($result1)) {
        // temp user array
      //  $product = array();
        $product["Name"] = $row["Name"];
        $product["Artist"] = $row["Artist"];
		//$product[ftask] = $row[ftask];
      // $product["Romantic"] = $row["Romantic"];
       // $product["description"] = $row["description"];
       // $product["created_at"] = $row["created_at"];
        //$product["updated_at"] = $row["updated_at"];
		
		if((strcmp($row["Name"],$Name)==0) && ($row[$ftask]==1))
		{
			$yes=1;
			$response["success"] = 1;
        $response["message"] = "Product successfully created.";
			// array_push($response["products"], $product);
		}


        // push single product into final response array
       // array_push($response["products"], $product);
    }
	if($yes!=1){
$result = mysql_query("INSERT INTO songs(Name, Artist, Album, Year) VALUES('$Name', '$Artist', '$Album', '$Year')");
	}
	}
	}
	
		
   
	}
	else
	{
		$response["success"]=0;
		$response["message"] = "Product not successfully created.";
	}
echo json_encode($response);
	

?>