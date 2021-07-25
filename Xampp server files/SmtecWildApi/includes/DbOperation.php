<?php
 
class DbOperation
{
    //Database connection link
    private $con;
 
    //Class constructor
    function __construct()
    {
        //Getting the DbConnect.php file
        require_once dirname(__FILE__) . '/DbConnect.php';
 
        //Creating a DbConnect object to connect to the database
        $db = new DbConnect();
 
        //Initializing our connection link of this class
        //by calling the method connect of DbConnect class
        $this->con = $db->connect();
    }
 
 /*
 * The create operation
 * When this method is called a new record is created in the database
 */
 function createHero($user_id, $condition_wild, $rotational_sequence){
 $stmt = $this->con->prepare("INSERT INTO wild_user (user_id, condition_wild, rotational_sequence) VALUES (?, ?, ?)");
 $stmt->bind_param("sss", $user_id, $condition_wild, $rotational_sequence);
 if($stmt->execute())
 return true; 
 return false; 
 }
 
 /*
 * The read operation
 * When this method is called it is returning all the existing record of the database
 */
 function getHeroes(){
 $stmt = $this->con->prepare("SELECT id, user_id, condition_wild, rotational_sequence FROM wild_user");
 $stmt->execute();
 $stmt->bind_result($id, $user_id, $condition_wild, $rotational_sequence);
 
 $wild_user = array(); 
 
 while($stmt->fetch()){
 $user  = array();
 $user['id'] = $id; 
 $user['user_id'] = $user_id; 
 $user['condition_wild'] = $condition_wild; 
 $user['rotational_sequence'] = $rotational_sequence; 

 
 array_push($wild_user, $user); 
 }
 
 return $wild_user; 
 }


 function createExperiment($user_id, $session, $date, $stimulus, $response, $edit_distance, $sentenceNo, $duration){
 $stmt = $this->con->prepare("INSERT INTO wild_experiment (user_id, session, date, stimulus, response,edit_distance, sentenceNo, duration ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
 $stmt->bind_param("sissssid", $user_id, $session, $date,$stimulus, $response, $edit_distance, $sentenceNo, $duration );
 if($stmt->execute())
 return true; 
 return false; 
 }


 function createRating($user_id, $session, $speed, $accuracy, $preference, $easeOfUse, $handPosture, $comment){
 $stmt = $this->con->prepare("INSERT INTO rating (user_id, session, speed, accuracy, preference,easeOfUse, handPosture, comment ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
 $stmt->bind_param("sidisdss", $user_id, $session, $speed, $accuracy, $preference, $easeOfUse, $handPosture, $comment );
 if($stmt->execute())
 return true; 
 return false; 
 }



 function createQuestionnaire($user_id, $move, $walk, $busy, $tired){
 $stmt = $this->con->prepare("INSERT INTO questionnaire (user_id, move, walk, busy, tired ) VALUES (?, ?, ?, ?, ?)");
 $stmt->bind_param("sdddd", $user_id, $move, $walk, $busy, $tired );
 if($stmt->execute())
 return true; 
 return false; 
 }
 }