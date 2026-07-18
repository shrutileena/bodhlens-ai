import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BackButton } from '../../shared/back-button/back-button';

@Component({
  selector: 'app-chat',
  imports: [BackButton],
  templateUrl: './chat.html',
  styleUrl: './chat.css',
})
export class Chat implements OnInit {

  private route = inject(ActivatedRoute);

  documentId!: string;

  ngOnInit(): void {
    
    this.documentId = this.route.snapshot.paramMap.get('documentId')!;

    console.log(this.documentId);
  }
}
