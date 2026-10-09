import React, { useState, useEffect } from 'react';
import { Bell, Trash2 } from 'lucide-react';
import { motion } from 'framer-motion';
import { Button } from '@/components/ui/button';

// Utilitaire pour formater la date
function formatRelativeDate(dateString: string) {
  const date = new Date(dateString);
  const now = new Date();
  const diff = Math.floor((now.getTime() - date.getTime()) / 1000);
  if (diff < 60) return `Il y a ${diff} secondes`;
  if (diff < 3600) return `Il y a ${Math.floor(diff / 60)} minutes`;
  if (diff < 86400) return `Il y a ${Math.floor(diff / 3600)} heures`;
  if (diff < 604800) return `Il y a ${Math.floor(diff / 86400)} jours`;
  return date.toLocaleDateString();
}

const Notifications = () => {
  const userId = localStorage.getItem('id');
  const token = localStorage.getItem('token');
  const [filter, setFilter] = useState<'all' | 'unread' | 'read'>('all');
  const [notifications, setNotifications] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);

  // Récupérer les notifications dynamiquement
  const fetchNotifications = async () => {
    if (!userId || !token) return;
    setLoading(true);
    try {
      const res = await fetch(`http://localhost:8080/api/notifications/user/${userId}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      const data = await res.json();
      setNotifications(data);
    } catch (e) {
      // Gérer l'erreur
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchNotifications();
    // eslint-disable-next-line
  }, []);

  // Filtrage dynamique
  const filteredNotifications = notifications.filter(n => {
    if (filter === 'all') return true;
    if (filter === 'unread') return !n.statut;
    if (filter === 'read') return n.statut;
    return true;
  });

  // Marquer tout comme lu
  const markAllAsRead = async () => {
    if (!userId || !token) return;
    await fetch(`http://localhost:8080/api/notifications/user/${userId}/mark-read`, {
      method: 'PUT',
      headers: { Authorization: `Bearer ${token}` }
    });
    fetchNotifications();
  };

  // Supprimer les notifications lues
  const deleteRead = async () => {
    if (!userId || !token) return;
    await fetch(`http://localhost:8080/api/notifications/user/${userId}/read`, {
      method: 'DELETE',
      headers: { Authorization: `Bearer ${token}` }
    });
    fetchNotifications();
  };

  const container = {
    hidden: { opacity: 0 },
    show: {
      opacity: 1,
      transition: { staggerChildren: 0.1 }
    }
  };
  const item = {
    hidden: { y: 20, opacity: 0 },
    show: { y: 0, opacity: 1 }
  };

  return (
    <div className="p-6 ml-64 animate-fadeIn">
      <div className="max-w-4xl mx-auto">
        <div className="flex items-center justify-between mb-6">
          <motion.h1 
            className="text-3xl font-bold text-gray-800 flex items-center"
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.5 }}
          >
            <Bell className="mr-3 h-7 w-7 text-green-600" />
            Notifications
          </motion.h1>
          <div className="flex gap-2">
            <Button
              variant="outline"
              className="text-sm"
              onClick={markAllAsRead}
              disabled={loading}
            >
              Tout marquer comme lu
            </Button>
            <Button
              variant="destructive"
              className="text-sm flex items-center"
              onClick={deleteRead}
              disabled={loading}
            >
              <Trash2 className="w-4 h-4 mr-1" />
              Supprimer les lus
            </Button>
          </div>
        </div>

        {/* Filtres */}
        <div className="flex gap-2 mb-6">
          <button
            className={`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
              filter === 'all'
                ? 'bg-violet-100 text-violet-700'
                : 'bg-white text-violet-700 border border-violet-100'
            }`}
            onClick={() => setFilter('all')}
          >
            Toutes
          </button>
          <button
            className={`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
              filter === 'unread'
                ? 'bg-violet-100 text-violet-700'
                : 'bg-white text-violet-700 border border-violet-100'
            }`}
            onClick={() => setFilter('unread')}
          >
            Non lues
          </button>
          <button
            className={`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
              filter === 'read'
                ? 'bg-violet-100 text-violet-700'
                : 'bg-white text-violet-700 border border-violet-100'
            }`}
            onClick={() => setFilter('read')}
          >
            Lues
          </button>
        </div>

        <motion.div 
          className="space-y-4"
          variants={container}
          initial="hidden"
          animate="show"
        >
          {loading ? (
            <div className="text-center py-12">Chargement...</div>
          ) : filteredNotifications.length > 0 ? (
            filteredNotifications.map((notification) => (
              <motion.div
                key={notification.id}
                variants={item}
                className={`w-full p-4 border ${
                  !notification.statut ? 'bg-green-50 border-green-200' : 'bg-white'
                } rounded-lg shadow-sm relative flex items-center`}
              >
                {!notification.statut && (
                  <div className="absolute top-4 right-4 w-2 h-2 rounded-full bg-green-500"></div>
                )}
                <div className={`bg-blue-100 p-3 rounded-full mr-4 flex-shrink-0`}>
                  <Bell className={`h-5 w-5 text-blue-500`} />
                </div>
                <div className="flex-1">
                  <h3 className="font-medium text-gray-800">{notification.textNotif}</h3>
                  <p className="text-gray-400 text-xs mt-2">{formatRelativeDate(notification.dateCreation)}</p>
                </div>
              </motion.div>
            ))
          ) : (
            <motion.div 
              className="text-center py-12 bg-white rounded-lg border"
              variants={item}
            >
              <div className="bg-gray-100 w-16 h-16 rounded-full mx-auto flex items-center justify-center mb-4">
                <Bell className="h-8 w-8 text-gray-400" />
              </div>
              <h3 className="text-lg font-medium text-gray-800">Aucune notification</h3>
              <p className="text-gray-500 mt-2">Vous serez notifié ici de toute activité importante</p>
            </motion.div>
          )}
        </motion.div>
      </div>
    </div>
  );
};

export default Notifications;
