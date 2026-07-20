import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth';
import { NotificationService } from '../../services/notification';

@Component({
  selector: 'app-dashboard',
  imports: [],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {

  private router = inject(Router);

  private authService = inject(AuthService);

  private notification = inject(NotificationService);

  firstName = '';

  ngOnInit(): void {
    this.firstName = this.authService.getFirstName() ?? 'User';
  }

  logout() {

    this.authService.logout();

    this.notification.success('Logged out successfully!')

    this.router.navigate(['/login']);
  }

  goToUpload() {
    this.router.navigate(['/upload-document']);
  }

  goToDocuments() {
    console.log('inside gotodocuments function');
    
    this.router.navigate(['/documents']);
  }

  goToChat() {
    this.router.navigate(['/chat'])
  }
  
  comingSoon() {
    this.notification.info('Coming Soon!');
  }
}
