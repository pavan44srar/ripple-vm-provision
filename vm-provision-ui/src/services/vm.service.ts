import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/vm/';

class VMService {
  getALLVM() {
    return axios.get(API_URL + 'all', { headers: authHeader() });
  }

  createVM(vmName: string, domainName: string, os: string, ramSize: string, hardDiskSize: string, noOfCPUCores: string) {
    return axios.post(API_URL + 'new',
      {
        vmName,
        domainName,
        os,
        ramSize,
        hardDiskSize,
        noOfCPUCores
      }
      , {
        headers: authHeader()
      }
    );
  }

}

export default new VMService();
