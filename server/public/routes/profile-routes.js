var url = "http://localhost:5001/voices-mumbai/us-central1/api/"
var uid = "";

function getReport(uid) {
    axios
        .get(`${url}report?uid=${uid}`)
        .then((response) => {
            let data = response.data;

            console.log(data);

            if (data.uid === uid) {
                document.getElementById("category").innerHTML = data.category;
                document.getElementById("upvotes").innerHTML = data.upvotes;
                document.getElementById("desc").innerHTML = data.description;

                document.getElementById("lat").innerHTML = data.latitude;
                document.getElementById("lng").innerHTML = data.longitude;

                document.getElementById("name").innerHTML = data.ward.name;
                document.getElementById("address").innerHTML = data.ward.address;
                document.getElementById("contact").innerHTML = data.ward.contacts[0];
                

                $("#image").attr("src", data.imageUrl);

                if (data.hasOwnProperty('mComments'))
                    generateList(data);
            }
        })
        .catch((err) => console.log(err));
}

function generateList(data) {

    data.mComments.forEach((comment) => {
        let str = `<li class="list-group-item">${comment.userName} : ${comment.commentText}</li>`;
      $("#comment-list").append(str);
    })
}

window.onload = () => {
    var params = new URLSearchParams(window.location.search);
    uid = params.get("uid");
    
    getReport(uid);
}