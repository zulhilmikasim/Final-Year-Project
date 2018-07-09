// file: script.js
$(document).ready(function(){
  //initialize the firebase app
 var config = {
    apiKey: "AIzaSyAcEpSR9PS97kqcc580dWLkUJQXS81vStk",
    authDomain: "user-45979.firebaseapp.com",
    databaseURL: "https://user-45979.firebaseio.com",
    projectId: "user-45979",
    storageBucket: "user-45979.appspot.com",
    messagingSenderId: "815458261401"
  };
  firebase.initializeApp(config);

  //create firebase references
  var Auth = firebase.auth(); 
  var dbRef = firebase.database();
  var contactsRef = dbRef.ref('contacts')
  
 

 $('#doLogin').on('click', function (e) {
    e.preventDefault();
    $('#loginModal').modal('hide');
    $('#messageModalLabel').html(spanText('<i class="fa fa-cog fa-spin"></i>', ['center', 'info']));
    $('#messageModal').modal('show');

    if( $('#loginEmail').val() != '' && $('#loginPassword').val() != '' ){
      //login the user
      var data = {
        email: $('#loginEmail').val(),
        password: $('#loginPassword').val()
      };
      firebase.auth().signInWithEmailAndPassword(data.email, data.password)
        .then(function(authData) {
          console.log("Authenticated successfully with payload:", authData);
          auth = authData;
          $('#messageModalLabel').html(spanText('Success!', ['center', 'success']))
          setTimeout(function () {
            $('#messageModal').modal('hide');
            $('.unauthenticated, .userAuth').toggleClass('unauthenticated').toggleClass('authenticated');
            contactsRef.child(auth.uid)
              .on("child_added", function(snap) {
                console.log("added", snap.key(), snap.val());
                $('#contacts').append(contactHtmlFromObject(snap.val()));
              });
          })
        })
        .catch(function(error) {
          console.log("Login Failed!", error);
          $('#messageModalLabel').html(spanText('ERROR: '+error.code, ['danger']))
        });
    }
  });
  
  
})
