const BASE_URL = "http://localhost:8080/login";

let form = document.getElementById("login_form");
let email = document.getElementById("email");
let password = document.getElementById("password");
let error = document.getElementById("error");

form?.addEventListener("submit", connect);

function connect(event) {
  event.preventDefault();

  fetch(BASE_URL ,{
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "token": sessionStorage.getItem("token"),
    },
    body: JSON.stringify({
      "email": email.value,
      "password": password.value,
    }),
  })
    .then((response) => response.json())
    .then((json) => redirect(json))
    .catch((error) => displayError(error));
}

function redirect(json) {
  error.classList.add("hidden");
  sessionStorage.setItem("token", json.token);
  location = "index.html";
}

function displayError(error) {
  error.classList.add("hidden");
}
