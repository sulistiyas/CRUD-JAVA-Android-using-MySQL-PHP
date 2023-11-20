<?php
require_once('dbConnect.php');

if($_SERVER['REQUEST_METHOD']=='POST') {

  $response = array();
  //mendapatkan data
  $npm = $_POST['npm'];
  $nama = $_POST['nama'];
  $kelas = $_POST['kelas'];
  $sesi = $_POST['sesi'];

  $sql = "UPDATE mahasiswa SET nama = '$nama', kelas = '$kelas', sesi = '$sesi' WHERE npm = '$npm'";
  if(mysqli_query($con,$sql)) {
    $response["value"] = 1;
    $response["message"] = "Berhasil diperbarui";
    echo json_encode($response);
  } else {
    $response["value"] = 0;
    $response["message"] = "oops! Gagal merubah!";
    echo json_encode($response);
  }
  mysqli_close($con);
}