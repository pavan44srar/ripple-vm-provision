import { Component } from "react";
import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";
import vmService from "../services/vm.service";
import { RouteComponentProps } from "react-router-dom";

interface RouterProps {
  history: string;
}
type Props = RouteComponentProps<RouterProps>;

type State = {
  vmName: string | null,
  domainName: string,
  os: string,
  ramSize: string,
  hardDiskSize: string,
  noOfCPUCores: string,
  successful: boolean,
  message: string
};

export default class CreateVM extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.handleCreateVM = this.handleCreateVM.bind(this);

    this.state = {
      vmName: "",
      domainName: "",
      os: "",
      ramSize: "",
      hardDiskSize: "",
      noOfCPUCores: "",
      successful: false,
      message: ""
    };
  }

  validationSchema() {
    const domainRegex = /^(?:[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?\.)+[a-z0-9][a-z0-9-]{0,61}[a-z0-9]$/;
    return Yup.object().shape({
      vmName: Yup.string()
        .test(
          "len",
          "Virtual Machine Name must be between 3 and 40 characters.",
          (val: any) =>
            val &&
            val.toString().length >= 3 &&
            val.toString().length <= 40
        )
        .required("Virtual Machine is required!"),
      domainName: Yup.string()
        .matches(domainRegex, "This is not a valid Domain Name.")
        .required("Domain Name is required!"),
      os: Yup.string()
        .notOneOf(
          [
            Yup.ref("Choose Operating System")
          ]
        )
        .required("Operating System is required!"),
      ramSize: Yup.string()
        .notOneOf(
          [
            Yup.ref("Choose RAM")
          ]
        )
        .required("RAM is required!"),
      hardDiskSize: Yup.string()
        .notOneOf(
          [
            Yup.ref("Choose Hard Disk")
          ]
        )
        .required("Hard Disk is required!"),
      noOfCPUCores: Yup.string()
        .notOneOf(
          [
            Yup.ref("Choose CPU Cores")
          ]
        )
        .required("CPU is required!")
    });
  }


  handleCreateVM(formValue: { vmName: string; domainName: string; os: string, ramSize: string, hardDiskSize: string, noOfCPUCores: string }) {
    const { vmName, domainName, os, ramSize, hardDiskSize, noOfCPUCores } = formValue;

    this.setState({
      message: "",
      successful: false
    });

    vmService.createVM(vmName, domainName, os, ramSize, hardDiskSize, noOfCPUCores).then(
      response => {
        this.setState({
          message: "VM Created Succesfully!",
          successful: true
        });
        this.props.history.push("/create");
        window.location.reload();
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
      vmName: "",
      domainName: "",
      os: "",
      ramSize: "",
      hardDiskSize: "",
      noOfCPUCores: ""
    };

    return (
      <div >
        <div><h2> Create Virtual Machines</h2></div>
        <hr></hr>
        <br />
        {message && (
          <div className="form-group">
            <div className={
              successful ? "alert alert-success" : "alert alert-danger"
            } role="alert">
              {message}
            </div>
          </div>
        )}
        <Formik
          initialValues={initialValues}
          validationSchema={this.validationSchema}
          onSubmit={this.handleCreateVM}>
          <Form>
            <div className="form-row">
              <div className="form-group col-md-6">
                <label htmlFor="vmName">Virtual Machine Name</label>
                <Field type="text" className="form-control" id="vmName" name="vmName" placeholder="Reporting Ubuntu Machine" />
                <ErrorMessage
                  name="vmName"
                  component="div"
                  className="alert alert-danger"
                />
              </div>
              <div className="form-group col-md-6">
                <label htmlFor="domainName">Domain Name</label>
                <Field type="text" className="form-control" id="domainName" name="domainName" placeholder="report.ripple.com" />
                <ErrorMessage
                  name="domainName"
                  component="div"
                  className="alert alert-danger"
                />
              </div>
            </div>
            <div className="form-row">
              <div className="form-group col-md-6">
                <label htmlFor="os">Operating System</label>
                <Field as="select" name="os" className="form-control">
                  <option selected>Choose Operating System</option>
                  <option value="Ubuntu">Ubuntu</option>
                  <option value="Linux">Linux</option>
                  <option value="RedHat">RedHat</option>
                  <option value="Windows Server">Windows Server</option>
                </Field>
                <ErrorMessage
                  name="os"
                  component="div"
                  className="alert alert-danger"
                />
              </div>
              <div className="form-group col-md-6">
                <label htmlFor="ramSize">Ram Size (GB)</label>
                <Field as="select" name="ramSize" className="form-control">
                  <option selected>Choose RAM</option>
                  <option value="16" >16</option>
                  <option value="32">32</option>
                  <option value="64">64</option>
                  <option value="128">128</option>
                </Field>
                <ErrorMessage
                  name="ramSize"
                  component="div"
                  className="alert alert-danger"
                />
              </div>
            </div>
            <div className="form-row">
              <div className="form-group col-md-6">
                <label htmlFor="hardDiskSize">Hard Disk Size (GB)</label>
                <Field as="select" name="hardDiskSize" className="form-control">
                  <option selected>Choose Hard Disk</option>
                  <option value="256">256</option>
                  <option value="512">512</option>
                  <option value="1024" >1024</option>
                  <option value="2048" >2048</option>
                </Field>
                <ErrorMessage
                  name="hardDiskSize"
                  component="div"
                  className="alert alert-danger"
                />
              </div>
              <div className="form-group col-md-6">
                <label htmlFor="noOfCPUCores">CPU Cores</label>
                <Field as="select" name="noOfCPUCores" className="form-control">
                  <option selected>Choose CPU Cores</option>
                  <option value="4">4</option>
                  <option value="8" >8</option>
                  <option value="10" >10</option>
                </Field>
                <ErrorMessage
                  name="noOfCPUCores"
                  component="div"
                  className="alert alert-danger"
                />
              </div>
            </div>
            <div className="col text-center p-5">
              <button type="submit" className="btn btn-primary ">Create</button>
            </div>
          </Form>
        </Formik>
      </div>
    );
  }
}
