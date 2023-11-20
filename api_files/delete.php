<?php
require_once('dbConnect.php');

if($_SERVER['REQUEST_METHOD']=='POST') {

  $response = array();
  //mendapatkan data
  $npm = $_POST['npm'];

  $sql = "DELETE FROM mahasiswa WHERE npm = '$npm'";
  if(mysqli_query($con,$sql)) {
    $response["value"] = 1;
    $response["message"] = "Berhasil dihapus";
    echo json_encode($response);
  } else {
    $response["value"] = 0;
    $response["message"] = "oops! Gagal dihapus!";
    echo json_encode($response);
  }
  mysqli_close($con);
}