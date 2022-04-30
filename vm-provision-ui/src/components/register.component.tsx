import { Component } from "react";
import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";

import AuthService from "../services/auth.service";
import { RouteComponentProps } from "react-router-dom";

interface RouterProps {
  history: string;
}
type Props = RouteComponentProps<RouterProps>;

type State = {
  name: string,
  email: string,
  password: string,
  designation: string,
  country: string,
  mobileNo: string,
  successful: boolean,
  message: string
};

export default class Register extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.handleRegister = this.handleRegister.bind(this);
    this.handleLogin = this.handleLogin.bind(this);

    this.state = {
      name: "",
      email: "",
      password: "",
      designation: "",
      country: "",
      mobileNo: "",
      successful: false,
      message: ""
    };
  }

  validationSchema() {
    return Yup.object().shape({
      name: Yup.string()
        .test(
          "len",
          "The Name must be between 3 and 40 characters.",
          (val: any) =>
            val &&
            val.toString().length >= 3 &&
            val.toString().length <= 40
        )
        .required("Name is required!"),
      email: Yup.string()
        .email("This is not a valid email.")
        .required("E-Mail is required!"),
      password: Yup.string()
        .test(
          "len",
          "The password must be between 6 and 40 characters.",
          (val: any) =>
            val &&
            val.toString().length >= 6 &&
            val.toString().length <= 40
        )
        .required("Password is required!"),
      designation: Yup.string()
        .required("Designation is Required"),
      country: Yup.string()
        .required("Country is Required"),
      mobileNo: Yup.string()
        .matches(/^[0-9]{10}$/, "Not Valid Phone Number")
        .test("len", "Phone Number should be of 10 digits",
          (val: any) =>
            val &&
            val.toString.length < 10
        ).required("Phone Number is Required")
    });
  }

  handleLogin() {
    this.props.history.push("/login");
    window.location.reload();
  }

  handleRegister(formValue: { name: string; email: string; password: string, designation: string, country: string, mobileNo: string }) {
    const { name, email, password, designation, country, mobileNo } = formValue;

    this.setState({
      message: "",
      successful: false
    });

    AuthService.register(
      name,
      email,
      password,
      designation,
      country,
      mobileNo,
      email
    ).then(
      response => {
        this.setState({
          message: "User Created Succcessfully",
          successful: true
        });
      },
      error => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        this.setState({
          successful: false,
          message: resMessage
        });
      }
    );
  }

  render() {
    const { successful, message } = this.state;

    const initialValues = {
      name: "",
      email: "",
      password: "",
      designation: "",
      country: "",
      mobileNo: ""
    };

    return (
      <div className="col-md-12">
        <div className="card card-container">
          <img
            src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
            alt="profile-img"
            className="profile-img-card"
          />

          <Formik
            initialValues={initialValues}
            validationSchema={this.validationSchema}
            onSubmit={this.handleRegister}
          >
            <Form>
              {message && (
                <div className="form-group">
                  <div
                    className={
                      successful ? "alert alert-success" : "alert alert-danger"
                    }
                    role="alert"
                  >
                    {message}

                  </div>
                  <div className="form-group d-flex justify-content-center p-5">
                    <button type="button" className="btn btn-primary btn-block" onClick={this.handleLogin}>Log In</button>
                  </div>
                </div>
              )}
              {!successful && (
                <div>

                  <div className="form-group">
                    <label htmlFor="name"> Name </label>
                    <Field name="name" type="text" className="form-control" />
                    <ErrorMessage
                      name="name"
                      component="div"
                      className="alert alert-danger"
                    />
                  </div>

                  <div className="form-group">
                    <label htmlFor="email"> Email </label>
                    <Field name="email" type="email" className="form-control" />
                    <ErrorMessage
                      name="email"
                      component="div"
                      className="alert alert-danger"
                    />
                  </div>

                  <div className="form-group">
                    <label htmlFor="password"> Password </label>
                    <Field
                      name="password"
                      type="password"
                      className="form-control"
                    />
                    <ErrorMessage
                      name="password"
                      component="div"
                      className="alert alert-danger"
                    />
                  </div>
                  <div className="form-group">
                    <label htmlFor="designation"> Designation </label>
                    <Field
                      name="designation"
                      type="text"
                      className="form-control"
                    />
                    <ErrorMessage
                      name="designation"
                      component="div"
                      className="alert alert-danger"
                    />
                  </div>
                  <div className="form-group">
                    <label htmlFor="country"> Country </label>
                    <Field
                      name="country"
                      type="text"
                      className="form-control"
                    />
                    <ErrorMessage
                      name="country"
                      component="div"
                      className="alert alert-danger"
                    />
                  </div>
                  <div className="form-group">
                    <label htmlFor="mobileNo"> Mobile No </label>
                    <Field maxLength={10}
                      name="mobileNo"
                      type="text"
                      className="form-control"
                    />
                    <ErrorMessage
                      name="mobileNo"
                      component="div"
                      className="alert alert-danger"
                    />
                  </div>

                  <div className="form-group d-flex justify-content-center p-5">
                    <button type="submit" className="btn btn-primary btn-block">Sign Up</button>
                  </div>
                </div>
              )}
            </Form>
          </Formik>
        </div>
      </div >
    );
  }
}
