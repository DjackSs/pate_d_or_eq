const BASE_URL = "http://localhost:8080";

let container = document.getElementById("container");

loadPage();

let logout = document.getElementById("logout");
logout.addEventListener("click", logUserOut);

function loadPage() {

  if (!sessionStorage.getItem("token")) {
    location = "login.html";
  }
  fetch(BASE_URL + "/pate_d_or/resa", {
    method: "GET", 
    headers: {
      "token": sessionStorage.getItem("token")
    }
  })
  .then(response => response.text())
  .then(content => displayContent(content));
}

function displayContent(content) {
  container.textContent = content;
}

function logUserOut() {
  fetch(BASE_URL + "/logout", {
    method: "GET",
    headers: {
      "token": sessionStorage.getItem("token")
    }
  })
  .then(_ => {
    sessionStorage.clear();
    location = "login.html";
  });
}
