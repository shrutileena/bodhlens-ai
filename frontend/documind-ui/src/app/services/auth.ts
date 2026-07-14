import { inject, Service } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from '../models/login-request';
import { RegisterRequest } from '../models/register-request';

@Service()
export class AuthService {

    private http = inject(HttpClient);

    login(loginRequest: LoginRequest) {
        console.log('sending api request')
        return this.http.post("http://localhost:8080/api/auth/login", loginRequest);
    }

    register(registerRequest: RegisterRequest) {
        return this.http.post("http://localhost:8080/api/auth/register", registerRequest);
    }
}
