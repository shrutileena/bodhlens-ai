import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { passwordMatchValidator } from '../../validators/password-match.validator';
import { AuthService } from '../../services/auth';
import { Router ,RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  registerForm = this.fb.nonNullable.group(
    {
      firstName: ['', Validators.required],

      lastName: ['', Validators.required],

      email: ['', [Validators.required, Validators.email]],

      password: ['', [Validators.required, Validators.minLength(8)]],

      confirmPassword: ['', Validators.required],
    },
    {
      validators: passwordMatchValidator(),
    },
  );

  register() {

    const formValue = this.registerForm.getRawValue();

    const registerRequest = {
      firstName: formValue.firstName,
      lastName: formValue.lastName,
      email: formValue.email,
      password: formValue.password
    }

    this.authService.register(registerRequest).subscribe({

      next: (response) => {
        console.log("Registration Successful");
        console.log(response);
        this.router.navigate(['/login']);
      },

      error: (error) => {
        console.log("Registration Failed");
        console.log(error);
      }
    });
  }
}
