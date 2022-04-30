import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
  login(username: string, password: string) {
    localStorage.setItem("userId", username);
    return axios
      .post(API_URL + "sign-in", {
        username,
        password
      })
      .then(response => {
        if (response.data.jwt) {
          localStorage.setItem("access-token", JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("access-token");
    localStorage.removeItem("userId");
  }

  register(name: string, email: string, password: string, designation: string, country: string, mobileNo: string, userId: string) {
    return axios.post(API_URL + "sign-up", {
      name,
      email,
      password,
      designation,
      country,
      mobileNo,
      userId
    });
  }

  getCurrentUser() {
    const userStr = localStorage.getItem("access-token");
    if (userStr) return JSON.parse(userStr);

    return null;
  }
}

export default new AuthService();
