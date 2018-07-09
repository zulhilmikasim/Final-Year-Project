// Initialize Firebase
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

// CREATE REWIEW

var reviewForm = document.getElementById('reviewForm');
var date   = document.getElementById('date');
var time = document.getElementById('time');
var pickup = document.getElementById('pickup');
var destination = document.getElementById('destination');
var regnum = document.getElementById('regnum');
var bname = document.getElementById('bname');
var price = document.getElementById('price');
var user_name   = document.getElementById('user_name');
var age = document.getElementById('age');
var address = document.getElementById('address');
var hiddenId   = document.getElementById('hiddenId');

reviewForm.addEventListener('submit', (e) => {
  e.preventDefault();

  if (!date.value || !time.value || !pickup.value || !destination.value || !bname.value|| !regnum.value || !price.value || !user_name.value || !age.value || !address.value) return null

  var id = hiddenId.value || Date.now()
  var myRef = db.ref('returnschedule');
  var db2 = myRef.push();
  var d1 =new Date(date.value).toJSON().slice(0,10).split('-').reverse().join('/');

  db2.set({
    date: d1,
	time: time.value,
	pickup:pickup.value,
	destination : destination.value,
	regnum : regnum.value,
	company : bname.value,
	price : price.value,
	name: user_name.value,
    age: age.value,
	address : address.value
  });

  date.value = '';
  time.value  = '';
  pickup.value  = '';
  destination.value = '';
  regnum.value = '';
  bname.value = '';
  price.value = '';
  user_name.value = '';
  age.value  = '';
  address.value = '';
  hiddenId.value = '';
  
  alert("Successfully added/edited");
});

// READ REVEIWS

var reviews = document.getElementById('reviews');
var reviewsRef = db.ref('/returnschedule');

reviewsRef.on('child_added', (data) => {
  var li = document.createElement('li')
  li.id = data.key;
  li.innerHTML = reviewTemplate(data.val())
  reviews.appendChild(li);
});

reviewsRef.on('child_changed', (data) => {
  var reviewNode = document.getElementById(data.key);
  reviewNode.innerHTML = reviewTemplate(data.val());
});

reviewsRef.on('child_removed', (data) => {
  var reviewNode = document.getElementById(data.key);
  reviewNode.parentNode.removeChild(reviewNode);
});

reviews.addEventListener('click', (e) => {
  var reviewNode = e.target.parentNode

  // UPDATE REVEIW
  if (e.target.classList.contains('edit')) {
    date.value = reviewNode.querySelector('.date').innerText;
	time.value  = reviewNode.querySelector('.time').innerText;
	pickup.value  = reviewNode.querySelector('.pickup').innerText;
	destination.value  = reviewNode.querySelector('.destination').innerText;
	regnum.value  = reviewNode.querySelector('.regnum').innerText;
	bname.value  = reviewNode.querySelector('.company').innerText;
	price.value  = reviewNode.querySelector('.price').innerText;
	 user_name.value = reviewNode.querySelector('.name').innerText;
    age.value  = reviewNode.querySelector('.age').innerText;
	address.value  = reviewNode.querySelector('.address').innerText;
    hiddenId.value = reviewNode.id;
	
  }

   // DELETE REVEIW
  if (e.target.classList.contains('delete')) {
    var id = reviewNode.id;
    db.ref('returnschedule/' + id).remove();
	alert("Successfully deleted");
  }
});

function reviewTemplate({date, time, pickup, destination, regnum, company, price,name, age, address}) {
  return `
	<label>Depart Date </label><li class='date'>${date}</li>
	<label>Pickup Point </label><li class='pickup'>${pickup}</li>
	<label>Destination </label><li class='destination'>${destination}</li>
	<label>Depart Time </label><li class='time'>${time}</li>
	<label>Bus Registration Number </label><li class='regnum'>${regnum}</li>
	<label>Bus Company Name </label><li class='company'>${company}</li>
	<label>Price(RM) </label><li class='price'>${price}</li>
	<label>Name </label><li class='name'>${name}</li>
    <label>Age </label><li class='age'>${age}</li>
    <label>Address </label><li class='address'>${address}</li>
    <button class='delete'>Delete</button>
    <button class='edit'>Edit</button></br></br></br>
	<hr class = "divider">
  `
};