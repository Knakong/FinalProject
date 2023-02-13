import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Message } from 'src/app/models/message';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';
import { MessageService } from 'src/app/services/message.service';

@Component({
  selector: 'app-create-message',
  templateUrl: './create-message.component.html',
  styleUrls: ['./create-message.component.css'],
})
export class CreateMessageComponent {
  @Input() receiverId:number = 0;
  @Input() message:Message = new Message();

  constructor(
    private auth: AuthService,
    private router: Router,
    private messageService: MessageService
  ) {}

  createNewMessage(message: Message) {
    message.receiver = new User();
    message.receiver.id = this.receiverId;
    message.sender = new User();
    message.sender.id = Number(localStorage.getItem('currentUserId'));

    this.messageService.create(message).subscribe({
      next: (data) => {
        console.log(data);
      },
      error: (err) => {
        console.error('Failed to send message');
        console.error(err);
      }
    });
  }
}