import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BackButton } from '../../shared/back-button/back-button';
import { DocumentService } from '../../services/document-service';
import { DocumentResponse } from '../../models/document-response';
import { DatePipe } from '@angular/common';
import { NotificationService } from '../../services/notification';

@Component({
  selector: 'app-documents',
  imports: [BackButton, DatePipe],
  templateUrl: './documents.html',
  styleUrl: './documents.css',
})
export class Documents implements OnInit {

  private router = inject(Router);

  private documentService = inject(DocumentService);

  private cdr = inject(ChangeDetectorRef);

  private notification = inject(NotificationService);

  documents: DocumentResponse[] = [];
  
  ngOnInit(): void {
    console.log('inside ngOnInit');
    
    this.loadDocuments();
  }

  uploadDocuments() {
    this.router.navigate(['/upload-document']);
  }

  private loadDocuments(): void {

    this.documentService.getDocuments().subscribe({
      next: (documents) => {
        console.log('Received:', documents);
        this.documents = documents;
        this.cdr.detectChanges();
        console.log('Length:', this.documents.length);
      },
      error: (error) => {
        console.log(error);
        
      }
    })
  }

  viewDocument(document: DocumentResponse): void {
    this.documentService.viewDocument(document.id).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        window.open(url, '_blank');
      },
      error: (error) => {
        console.log(error);
        this.notification.error('Failed to open document');
      }
    });
  }

  chat(document: DocumentResponse): void {
    this.router.navigate(['/chat', document.id]);
  }

  deleteDocument(document: DocumentResponse): void {
    this.documentService.deleteDocument(document.id).subscribe({
      next: (response) => {
        this.notification.success(response.message);
        this.loadDocuments();
      },
      error: (error) => {
        console.log(error);
        this.notification.error('Failed to delete document');
      }
    })
  }
}
