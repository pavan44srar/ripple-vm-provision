import { Component } from "react";
import AuthService from "../services/auth.service";
import IUser from "../types/user.type";
import VMService from "../services/vm.service";
import EventBus from "../common/EventBus";
import { RouteComponentProps } from "react-router-dom";
import DataGrid from 'react-data-grid';

interface RouterProps {
  history: string;
}
type Props = RouteComponentProps<RouterProps>;


type State = {
  content: any[],
  message: string,
  currentUser: IUser & { accessToken: string }
}

export default class Home extends Component<Props, State> {
  constructor(props: Props) {
    super(props);

    this.state = {
      content: [],
      message: "",
      currentUser: { accessToken: "" }
    };
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();
    if (!currentUser) {
      this.props.history.push("/login");
      window.location.reload();
    }
    VMService.getALLVM().then(
      (response) => {
        console.log(response.data);
        this.setState({
          content: response.data,
          currentUser: { accessToken: "" }
        });
        console.log(this.state);
      },
      error => {
        this.setState({
          message:
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString()
        });

        if (error.response && error.response.status === 401) {
          EventBus.dispatch("logout");
        }
      });
  }

  render() {
    let message = this.state.message;
    const columns = [
      { key: 'id', name: 'ID' },
      { key: 'vmName', name: 'Virtual Machine Name', width: 200 },
      { key: 'domainName', name: 'Domain Name', width: 200 },
      { key: 'os', name: 'Operating System', width: 200 },
      { key: 'ramSize', name: 'Ram Size (GB)', width: 200 },
      { key: 'hardDiskSize', name: 'Hard Disk Size (GB)', width: 200 },
      { key: 'noOfCPUCores', name: 'CPU Cores', width: 200 },
      { key: 'userId', name: 'Created By', width: 200 },
    ];
    return (
      <div>
        {message && (
          <div className="form-group">
            <div className="alert alert-danger" role="alert">
              {message}
            </div>
          </div>
        )}<h1>Virtual Machines</h1>
        <DataGrid columns={columns} rows={this.state.content}></DataGrid></div>
    );
  }
}
