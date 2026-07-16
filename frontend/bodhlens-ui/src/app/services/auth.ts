import { inject, Service } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from '../models/login-request';
import { RegisterRequest } from '../models/register-request';
import { LoginResponse } from '../models/login-response';

@Service()
export class AuthService {

    private http = inject(HttpClient);

    login(loginRequest: LoginRequest) {
        console.log('sending api request')
        return this.http.post<LoginResponse>("http://localhost:8080/api/auth/login", loginRequest);
    }

    register(registerRequest: RegisterRequest) {
        return this.http.post("http://localhost:8080/api/auth/register", registerRequest);
    }

    saveToken(token: string) {
        localStorage.setItem('token', token);
    }

    getToken(): string | null {
        return localStorage.getItem('token');
    }

    isLoggedIn(): boolean {
        return this.getToken() !== null;
    }

    logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('firstName');
    }

    saveFirstName(firstName: string) {
        localStorage.setItem('firstName', firstName);
    }

    getFirstName(): string | null {
        return localStorage.getItem('firstName');
    }
}
