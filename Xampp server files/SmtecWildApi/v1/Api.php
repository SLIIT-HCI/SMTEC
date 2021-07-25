<?php 
 
 //getting the dboperation class
 require_once '../includes/DbOperation.php';
 
 //function validating all the paramters are available
 //we will pass the required parameters to this function 
 function isTheseParametersAvailable($params){
 //assuming all parameters are available 
 $available = true; 
 $missingparams = ""; 
 
 foreach($params as $param){
 if(!isset($_POST[$param]) || strlen($_POST[$param])<=0){
 $available = false; 
 $missingparams = $missingparams . ", " . $param; 
 }
 }
 
 //if parameters are missing 
 if(!$available){
 $response = array(); 
 $response['error'] = true; 
 $response['message'] = 'Parameters ' . substr($missingparams, 1, strlen($missingparams)) . ' missing';
 
 //displaying error
 echo json_encode($response);
 
 //stopping further execution
 die();
 }
 }
 
 //an array to display response
 $response = array();
 
 //if it is an api call 
 //that means a get parameter named api call is set in the URL 
 //and with this parameter we are concluding that it is an api call
 if(isset($_GET['apicall'])){
 
 switch($_GET['apicall']){
 
 //the CREATE operation
 //if the api call value is 'createhero'
 //we will create a record in the database
 case 'createhero':
 //first check the parameters required for this request are available or not 
 isTheseParametersAvailable(array('user_id','condition_wild','rotational_sequence'));
 

 //creating a new dboperation object
 $db = new DbOperation();
 
 //creating a new record in the database
 $result = $db->createHero(
 $_POST['user_id'],
 $_POST['condition_wild'],
 $_POST['rotational_sequence']
 );
 
 
 //if the record is created adding success to response
 if($result){
 //record is created means there is no error
 $response['error'] = false; 
 
 //in message we have a success message
 $response['message'] = 'Addedd successfully';
 
 //and we are getting all the heroes from the database in the response
 $response['heroes'] = $db->getHeroes();
 }else{
 
 //if record is not added that means there is an error 
 $response['error'] = true; 
 
 //and we have the error message
 $response['message'] = 'Some error occurred please try again';
 }
 
 break; 
 
 //the READ operation
 //if the call is getheroes
 case 'getheroes':
 $db = new DbOperation();
 $response['error'] = false; 
 $response['message'] = 'Request successfully completed';
 $response['wild_user'] = $db->getHeroes();
 break; 
 

//creating experiment
  case 'create_experiment':
 //first check the parameters required for this request are available or not 
 isTheseParametersAvailable(array('user_id','session','date','stimulus', 'response','edit_distance','sentenceNo','duration'));
 

 //creating a new dboperation object
 $db = new DbOperation();
 
 //creating a new record in the database
 $result = $db->createExperiment(
 $_POST['user_id'],
 $_POST['session'],
 $_POST['date'],
 $_POST['stimulus'],
 $_POST['response'],
 $_POST['edit_distance'],
 $_POST['sentenceNo'],
 $_POST['duration']
 );



 //creating rating
 case 'create_rating':
 //first check the parameters required for this request are available or not 
 isTheseParametersAvailable(array('user_id', 'session', 'speed', 'accuracy', 'preference','easeOfUse', 'handPosture', 'comment'));
 

 //creating a new dboperation object
 $db = new DbOperation();
 
 //creating a new record in the database
 $result = $db->createRating(
 $_POST['user_id'],
 $_POST['session'],
 $_POST['speed'],
 $_POST['accuracy'],
 $_POST['preference'],
 $_POST['easeOfUse'],
 $_POST['handPosture'],
 $_POST['comment']
 );



//creating questionnaire
   case 'create_questionnaire':
 //first check the parameters required for this request are available or not 
 isTheseParametersAvailable(array('user_id','move','walk','busy', 'tired'));
 

 //creating a new dboperation object
 $db = new DbOperation();
 
 //creating a new record in the database
 $result = $db->createQuestionnaire(
 $_POST['user_id'],
 $_POST['move'],
 $_POST['walk'],
 $_POST['busy'],
 $_POST['tired']
 );


 
 }
 
 }else{
 //if it is not api call 
 //pushing appropriate values to response array 
 $response['error'] = true; 
 $response['message'] = 'Invalid API Call';
 }
 
 //displaying the response in json structure 
 echo json_encode($response);