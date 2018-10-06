var url = "http://localhost:5001/voices-mumbai/us-central1/api/"

function getReport(uid) {
    axios
        .get(`${url}report?uid=${uid}`)
        .then((response) => {
            let data = response.data;

            console.log(data);

            if (data.uid === uid) {
                document.getElementById("category").innerHTML = data.category;
                document.getElementById("upvotes").innerHTML = data.upvotes;
                $("#image").attr("src", data.imageUrl);
            }
        })
        .catch((err) => console.log(err));
}

function getComments(uid) {
    axios
        .get(`${url}report/comment?uid=${uid}`)
        .then((response) => {
            let data = response.data;

            console.log(data);

            if (data.uid === uid) {
                document.getElementById("category").innerHTML = data.category;
                document.getElementById("upvotes").innerHTML = data.upvotes;
                $("#image").attr("src", data.imageUrl);
            }
        })
        .catch((err) => console.log(err));
}

window.onload = () => {
    var params = new URLSearchParams(window.location.search);
    let uid = params.get("uid");
    
    getReport(uid);
}