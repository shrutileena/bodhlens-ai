import { Component, inject } from '@angular/core';
import { AuthService } from '../../services/auth';
import { NonNullableFormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { NotificationService } from '../../services/notification';

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

  private notification = inject(NotificationService);

  loginForm = this.fb.group({
    email: [''],
    password: ['']
  })

  login() {

    console.log('login method called!');
    
    const loginRequest = this.loginForm.getRawValue();

    this.authService.login(loginRequest).subscribe({
      next: (response) => {
        // console.log("Login successful", response);
        this.notification.success('Login successful! Welcome Back.');
        this.authService.saveToken(response.token);
        this.authService.saveFirstName(response.firstName);
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        console.log("Login failed", error);
        this.notification.error('Login Failed!');
      }
    });
  }
}
