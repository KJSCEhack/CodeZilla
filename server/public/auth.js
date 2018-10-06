function initApp() {

    firebase.auth().onAuthStateChanged(function (user) {
        if (user) {
            // User is signed in.
            var displayName = user.displayName;
            var email = user.email;
            var emailVerified = user.emailVerified;
            var photoURL = user.photoURL;
            var isAnonymous = user.isAnonymous;
            var uid = user.uid;
            var providerData = user.providerData;
        }
    });
}

function toggleSignIn() {
    if (firebase.auth().currentUser) {
        console.log('Sign out');
        firebase.auth().signOut();
    } else {
        var email = document.getElementById('email').value;
        var password = document.getElementById('password').value;

        if (email.length < 4) {
            alert('Please enter an email address.');

            return;
        }
        if (password.length < 4) {

            alert('Please enter a password.');
            return;
        }

        signIn(email, password);
    }
}

function signIn(email, password) {
    firebase.auth().signInWithEmailAndPassword(email, password).then(() => {
        window.location.href = "../index.html";
    }).catch(function (error) {
        var errorCode = error.code;
        var errorMessage = error.message;

        if (errorCode === 'auth/wrong-password') {
            alert('Wrong password.');
        } else {
            alert(errorMessage);
        }
        console.log(error);
        document.getElementById('signIn').disabled = true;
    });
}

function signUp(email, password) {
    firebase.auth().createUserWithEmailAndPassword(email, password).catch(function (error) {
        var errorCode = error.code;
        var errorMessage = error.message;
        if (errorCode === 'auth/weak-password') {
            alert('The password is too weak.');
        } else {
            alert(errorMessage);
        }
        console.log(error);
    });
}

function handleSignUp() {
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;
    if (email.length < 4) {
        alert('Please enter an email address.');
        return;
    }

    if (password.length < 4) {
        alert('Please enter a password.');
        return;
    }

    signUp(email, password);    
}

function signOut() {

    console.log("Signing out");

    if (firebase.auth().currentUser) {
        console.log('Sign out');
        firebase.auth().signOut();
        window.location.href = "login.html";
    }
}
