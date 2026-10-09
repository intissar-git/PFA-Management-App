import React, { useEffect, useState } from 'react';
import { Bell, MessageSquare, AlertTriangle, FileText } from 'lucide-react';

const Notifications = () => {
  const userId = localStorage.getItem('id');
  const token = localStorage.getItem('token');
  const [notifications, setNotifications] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);

  const getNotificationIcon = (type: string) => {
    switch (type) {
      case 'message':
        return <MessageSquare size={16} className="text-blue-500" />;
      case 'task':
        return <FileText size={16} className="text-green-500" />;
      case 'project':
        return <Bell size={16} className="text-violet-500" />;
      case 'alert':
        return <AlertTriangle size={16} className="text-red-500" />;
      default:
        return <Bell size={16} className="text-violet-500" />;
    }
  };

  // Récupérer dynamiquement les notifications
  const fetchNotifications = async () => {
    if (!userId || !token) return;
    setLoading(true);
    try {
      const res = await fetch(`http://localhost:8080/api/notifications/user/${userId}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      if (!res.ok) throw new Error('Erreur lors de la récupération');
      const data = await res.json();
      setNotifications(data);
    } catch (e) {
      setNotifications([]);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchNotifications();
    // eslint-disable-next-line
  }, []);

  // Marquer tout comme lu
  const markAllAsRead = async () => {
    if (!userId || !token) return;
    setLoading(true);
    try {
      await fetch(`http://localhost:8080/api/notifications/user/${userId}/mark-read`, {
        method: 'PUT',
        headers: { Authorization: `Bearer ${token}` }
      });
      fetchNotifications();
    } catch (e) {
      setLoading(false);
    }
  };

  return (
    <div className="dashboard-card h-full">
      <div className="p-5 border-b border-violet-100 flex justify-between items-center">
        <h2 className="text-lg font-medium text-violet-900">Notifications</h2>
        <span className="inline-flex items-center justify-center w-6 h-6 text-xs font-medium text-white bg-violet-500 rounded-full">
          {notifications.filter(n => !n.statut).length}
        </span>
      </div>
      <div className="divide-y divide-violet-100 max-h-[400px] overflow-y-auto">
        {loading ? (
          <div className="p-4 text-center text-violet-500">Chargement...</div>
        ) : notifications.length === 0 ? (
          <div className="p-8 text-center text-violet-400">Aucune notification</div>
        ) : (
          notifications.map((notification) => (
            <div key={notification.id} className={`p-4 notification-item ${!notification.statut ? 'bg-violet-50' : ''}`}>
              <div className="flex items-start gap-3">
                <div className="mt-0.5">
                  {getNotificationIcon(notification.type)}
                </div>
                <div className="flex-1">
                  <h3 className="font-medium text-violet-900 mb-1">{notification.textNotif}</h3>
                  <div className="text-xs text-violet-500">
                    {new Date(notification.dateCreation).toLocaleString()}
                  </div>
                </div>
                {!notification.statut && (
                  <div className="w-2 h-2 rounded-full bg-violet-500 mt-1"></div>
                )}
              </div>
            </div>
          ))
        )}
      </div>
      <div className="p-4 border-t border-violet-100">
        <button
          className="w-full py-2 bg-violet-50 hover:bg-violet-100 text-violet-700 rounded-md transition-colors text-sm font-medium"
          onClick={markAllAsRead}
          disabled={loading}
        >
          Marquer tout comme lu
        </button>
      </div>
    </div>
  );
};

export default Notifications;
