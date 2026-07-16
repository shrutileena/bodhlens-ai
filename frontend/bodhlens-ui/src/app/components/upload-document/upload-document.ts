import { Component, ElementRef, inject, ViewChild } from '@angular/core';
import { NotificationService } from '../../services/notification';
import { DocumentUpload } from '../../services/document-upload';

@Component({
  selector: 'app-upload-document',
  imports: [],
  templateUrl: './upload-document.html',
  styleUrl: './upload-document.css',
})
export class UploadDocument {

  private notification = inject(NotificationService);

  private documentUpload = inject(DocumentUpload);

  selectedFile: File | null = null;

  @ViewChild('fileInput') fileInput!: ElementRef;

  onFileSelected(event: Event) {

    const input = event.target as HTMLInputElement;

    if(input.files?.length) {

      this.selectedFile = input.files[0];
    }
  }

  upload() {

    if(!this.selectedFile) {
      this.notification.error('Please select a file.');
      return;
    }

    this.documentUpload.upload(this.selectedFile).subscribe({
      next: (response) => {

        console.log(response);

        this.notification.success('Document uploaded successfully');

        this.selectedFile = null;

        // this.fileInput.nativeElement.value = '';

      },
      error: (error) => {

        console.log(error);

        this.notification.error('Document upload failed');
        
      }
    });
    console.log(this.selectedFile);
  }
}
