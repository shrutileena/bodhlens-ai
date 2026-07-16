import { Service } from '@angular/core';
import { toast } from 'ngx-sonner';

@Service()
export class NotificationService {

    success(message: string, duration = 3000): void {
        toast.success(message, {
            duration
        });
    }

    error(message: string, duration = 3000): void {
        toast.error(message, {
            duration
        });
    }

    warning(message: string, duration = 3000): void {
        toast.warning(message, {
            duration
        });
    }

    info(message: string, duration = 3000): void {
        toast.info(message, {
            duration
        });
    }
}
