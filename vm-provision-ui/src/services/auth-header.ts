export default function authHeader() {
  const userStr = localStorage.getItem("access-token");
  let user = null;
  if (userStr)
    user = JSON.parse(userStr);

  if (user && user.jwt) {
    return { Authorization: 'Bearer ' + user.jwt, userId: localStorage.getItem("userId") }; // for Spring Boot back-end
  } else {
    return {};
  }
}