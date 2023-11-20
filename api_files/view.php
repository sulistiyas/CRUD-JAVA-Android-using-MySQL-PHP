<?php
require_once('dbConnect.php');
if($_SERVER['REQUEST_METHOD']=='GET') {
  $sql = "SELECT * FROM mahasiswa ORDER BY nama ASC";
  $res = mysqli_query($con,$sql);
  $result = array();
  while($row = mysqli_fetch_array($res)){
    array_push($result, array('npm'=>$row[0], 'nama'=>$row[1], 'kelas'=>$row[2], 'sesi'=>$row[3]));
  }
  echo json_encode(array("value"=>1,"result"=>$result));
  mysqli_close($con);
}