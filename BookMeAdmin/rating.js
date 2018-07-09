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

//create review
var hiddenId   = document.getElementById('hiddenId');
var rate   = document.getElementById('rate');
var comment = document.getElementById('comment');
var reg = document.getElementById('reg');


//ready
var reviews = document.getElementById('reviews');
var reviewsRef = db.ref('/Rating');

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

//event listener
reviews.addEventListener('click', (e) => {
  var reviewNode = e.target.parentNode
  // DELETE REVEIW
  if (e.target.classList.contains('delete')) {
    var id = reviewNode.id;
    db.ref('Rating/' + id).remove();
	alert("Successfully deleted");
  }
});

function reviewTemplate({rate, comment, reg}) {
  return `
  
	<label>Rating</label><li class='rate'>${rate}</li>
    <label>Comment </label><li class='comment'>${comment}</li>
	<label>Bus Registration Number </label><li class='reg'>${reg}</li>
	<hr class = "divider">

  `
};