import React from 'react';

const NotificationItem = ({ notification, formatRelativeDate }: { notification: any, formatRelativeDate: (date: string) => string }) => (
  <div className={`p-4 ${!notification.statut ? 'bg-violet-50' : ''}`}>
    <div className="font-medium">{notification.textNotif}</div>
    <div className="text-xs text-violet-500">
      {formatRelativeDate(notification.dateCreation)}
    </div>
  </div>
);

export default NotificationItem;
