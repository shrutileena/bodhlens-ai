import { Component, inject } from '@angular/core';
import { AuthService } from '../../services/auth';
import { NonNullableFormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  private authService = inject(AuthService);

  private fb = inject(NonNullableFormBuilder);

  loginForm = this.fb.group({
    email: [''],
    password: ['']
  })

  login() {

    console.log('login method called!');
    
    const loginRequest = this.loginForm.getRawValue();

    this.authService.login(loginRequest).subscribe({
      next: (response) => {
        console.log("Login successful", response);
      },
      error: (error) => {
        console.log("Login failed", error);
      }
    });
  }
}
