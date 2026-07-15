import { Component, inject } from '@angular/core';
import { AuthService } from '../../services/auth';
import { NonNullableFormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  private authService = inject(AuthService);

  private fb = inject(NonNullableFormBuilder);

  private router = inject(Router);

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
        this.authService.saveToken(response.token);
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        console.log("Login failed", error);
      }
    });
  }
}
