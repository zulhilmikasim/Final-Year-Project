 var config = {
    apiKey: "AIzaSyAcEpSR9PS97kqcc580dWLkUJQXS81vStk",
    authDomain: "user-45979.firebaseapp.com",
    databaseURL: "https://user-45979.firebaseio.com",
    projectId: "user-45979",
    storageBucket: "user-45979.appspot.com",
    messagingSenderId: "815458261401"
  };
  firebase.initializeApp(config);

var db = firebase.database();

var name   = document.getElementById('name');
var phone = document.getElementById('phone');
var regnum = document.getElementById('plate').value;


//ready
var reviews = document.getElementById('reviews');
var reviewsRef = db.ref('PassengerBooking').child(regnum);

reviewsRef.on('child_added', (data) => {
 var li = document.createElement('li')
  li.id = data.key;
  li.innerHTML = reviewTemplate(data.val())
  reviews.appendChild(li);
});


function reviewTemplate({name, phone,regnum}) {
  return `
  
    <label>Customer Name </label><li class='name'>${name}</li>
    <label>Customer Mobile No. </label><li class='phone'>${phone}</li>
   <hr class = "divider">
  `
};