/**
 * Description: Variables and functions for backend API
 */
const baseURL = "http://localhost:8080";

// API ENDPOINTS
export const API = Object.freeze({
  SIGNUP: {
    url: baseURL + "/api/auth/signup",
    contentType: "application/json",
    payload: {
      username: null,
      email: null,
      password: null,
      firstname: null,
      name: null,
      cycle_color: "rgb(0,0,0)",
      ranking: 0,
      roles: ["user"],
    },
  },
  SIGNIN: {
    url: baseURL + "/api/auth/signin",
    contentType: "application/json",
    payload: {
      username: null,
      password: null,
    },
  },
});

/**
 * Signin with username and password
 * @param {*} user
 * @param {*} callback
 */
export async function signIn(user, callback) {
  var responseData = { responseCode: 0, data: null };
  fetch(API.SIGNIN.url, {
    method: "POST",
    headers: {
      Accept: API.SIGNIN.contentType,
      "Content-Type": API.SIGNIN.contentType,
    },
    body: JSON.stringify({
      ...API.SIGNIN.payload,
      username: user.username,
      password: user.password,
    }),
  })
    .then((res) => {
      responseData.responseCode = res.status;
      return res.json();
    })
    .then((data) => {
      responseData.data = data;
      if (callback) {
        callback(responseData);
      }
    })
    .catch((error) => {
      console.log(error);
    });
}

/**
 * Signup with all userdata
 * @param {*} user
 * @param {*} callback
 */
export async function signUp(user, callback) {
  var responseData = { responseCode: 0, data: null };
  fetch(API.SIGNUP.url, {
    method: "POST",
    headers: {
      Accept: API.SIGNUP.contentType,
      "Content-Type": API.SIGNUP.contentType,
    },
    body: JSON.stringify({
      ...API.SIGNUP.payload,
      username: user.username,
      password: user.password,
      firstname: user.firstname,
      name: user.lastname,
      cycle_color: user.cycle_color,
    }),
  })
    .then((res) => {
      responseData.responseCode = res.status;
      return res.json();
    })
    .then((data) => {
      responseData.data = data;
      if (callback) {
        callback(responseData);
      }
    })
    .catch((error) => {
      console.log(error);
    });
}
