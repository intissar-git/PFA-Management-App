import React, { useState, useEffect } from 'react';
import DashboardLayout from '@/components/Layout/DashboardLayout';
import NotificationItem from '@/components/NotificationItem';
import { Trash2, CheckSquare } from 'lucide-react';
import { toast } from 'sonner';

// Fonction robuste pour formater la date
function formatRelativeDate(dateString: string) {
  if (!dateString) return '';
  const date = new Date(dateString);
  if (isNaN(date.getTime())) return '';
  const now = new Date();
  const diff = Math.floor((now.getTime() - date.getTime()) / 1000);
  if (diff < 60) return `il y a ${diff} secondes`;
  if (diff < 3600) return `il y a ${Math.floor(diff / 60)} minutes`;
  if (diff < 86400) return `il y a ${Math.floor(diff / 3600)} heures`;
  if (diff < 604800) return `il y a ${Math.floor(diff / 86400)} jours`;
  return date.toLocaleDateString();
}

const NotificationsPage = () => {
  const userId = localStorage.getItem('id');
  const token = localStorage.getItem('token');
  const [notifications, setNotifications] = useState<any[]>([]);
  const [filter, setFilter] = useState<'all' | 'unread' | 'read'>('all');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
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
    fetchNotifications();
  }, [userId, token]);

  const filteredNotifications = filter === 'all'
    ? notifications
    : filter === 'unread'
      ? notifications.filter(n => !n.statut)
      : notifications.filter(n => n.statut);

  const handleMarkAsRead = async (id: number) => {
    if (!userId || !token) return;
    setLoading(true);
    try {
      await fetch(`http://localhost:8080/api/notifications/${id}/mark-read`, {
        method: 'PUT',
        headers: { Authorization: `Bearer ${token}` }
      });
      setNotifications(notifications.map(notification =>
        notification.id === id ? { ...notification, statut: true } : notification
      ));
      toast.success("Notification marquée comme lue");
    } catch (e) {
      toast.error("Erreur lors du marquage");
    }
    setLoading(false);
  };

  const handleMarkAllAsRead = async () => {
    if (!userId || !token) return;
    setLoading(true);
    try {
      await fetch(`http://localhost:8080/api/notifications/user/${userId}/mark-read`, {
        method: 'PUT',
        headers: { Authorization: `Bearer ${token}` }
      });
      setNotifications(notifications.map(notification => ({ ...notification, statut: true })));
      toast.success("Toutes les notifications ont été marquées comme lues");
    } catch (e) {
      toast.error("Erreur lors du marquage");
    }
    setLoading(false);
  };

  const handleDeleteRead = async () => {
    if (!userId || !token) return;
    setLoading(true);
    try {
      await fetch(`http://localhost:8080/api/notifications/user/${userId}/read`, {
        method: 'DELETE',
        headers: { Authorization: `Bearer ${token}` }
      });
      setNotifications(notifications.filter(notification => !notification.statut));
      toast.success("Notifications lues supprimées");
    } catch (e) {
      toast.error("Erreur lors de la suppression");
    }
    setLoading(false);
  };

  return (
    <DashboardLayout>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-semibold text-violet-900">Notifications</h1>
        <div className="flex space-x-3">
          <button
            className="flex items-center px-4 py-2 text-sm font-medium text-violet-700 bg-violet-100 rounded-lg hover:bg-violet-200 transition-colors"
            onClick={handleMarkAllAsRead}
            disabled={loading}
          >
            <CheckSquare size={16} className="mr-2" />
            Tout marquer comme lu
          </button>
          <button
            className="flex items-center px-4 py-2 text-sm font-medium text-red-700 bg-red-50 rounded-lg hover:bg-red-100 transition-colors"
            onClick={handleDeleteRead}
            disabled={loading}
          >
            <Trash2 size={16} className="mr-2" />
            Supprimer les lus
          </button>
        </div>
      </div>

      <div className="flex space-x-2 mb-6">
        <button
          className={`px-4 py-2 rounded-lg text-sm font-medium ${
            filter === 'all'
              ? 'bg-violet-100 text-violet-700'
              : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
          }`}
          onClick={() => setFilter('all')}
        >
          Toutes
        </button>
        <button
          className={`px-4 py-2 rounded-lg text-sm font-medium ${
            filter === 'unread'
              ? 'bg-violet-100 text-violet-700'
              : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
          }`}
          onClick={() => setFilter('unread')}
        >
          Non lues
        </button>
        <button
          className={`px-4 py-2 rounded-lg text-sm font-medium ${
            filter === 'read'
              ? 'bg-violet-100 text-violet-700'
              : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
          }`}
          onClick={() => setFilter('read')}
        >
          Lues
        </button>
      </div>

      <div className="bg-white rounded-xl border border-violet-100 shadow-sm overflow-hidden">
        {loading ? (
          <div className="p-8 text-center text-violet-500">Chargement...</div>
        ) : filteredNotifications.length > 0 ? (
          <div className="divide-y divide-violet-100">
            {filteredNotifications.map(notification => (
              <NotificationItem
                key={notification.id}
                notification={notification}
                formatRelativeDate={formatRelativeDate}
                // onMarkAsRead={handleMarkAsRead} // <-- Retire ce prop si tu ne veux plus le bouton
              />
            ))}
          </div>
        ) : (
          <div className="p-8 text-center">
            <p className="text-violet-500">Aucune notification {filter !== 'all' ? (filter === 'unread' ? 'non lue' : 'lue') : ''}</p>
          </div>
        )}
      </div>
    </DashboardLayout>
  );
};

export default NotificationsPage;
