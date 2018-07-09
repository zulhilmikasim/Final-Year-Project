<?php

	$url = "https://fcm.googleapis.com/fcm/send";
		$token = "cgsOQ8_vLXw:APA91bEECn9OTtE0ZSSUYpMXjrbbVSBq-a3mlxETmWUKbxeyexMBpLYvC04nZhMtQjWnuYFDxweLZdJj1zFcp8TvYl4kcmHXJygxYqONUW53VisH7WplalvS7uF_Ofn8N-FArPGSz5Ie";
		$serverKey = 'AIzaSyBQGKgl3WBsrXT7uxhSF71335iOPIgf1uM';
		$value = $_REQUEST['title'];
		$value2 = $_REQUEST['message'];
		$title = $value;
		$body = $value2;
		$headers = array( 'Authorization:key=' .$serverKey,
					  'Content-Type:application/json');
	
		$fields = array('to' => $token,
						'notification'=>array('title'=>$title,'body'=>$body));
	
		$payload = json_encode($fields);
	
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, $url);
		curl_setopt($ch, CURLOPT_POST, true);
		curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
		curl_setopt($ch, CURLOPT_POSTFIELDS, $payload);
			$result = curl_exec($ch);
		if ($result === FALSE) {
			die('Oops! FCM Send Error: ' . curl_error($ch));
		}
		curl_close($ch);
		
		echo "<script>
alert('Announcement has been posted successfully.');
window.location.href='announcement.html';
</script>";
    
?>