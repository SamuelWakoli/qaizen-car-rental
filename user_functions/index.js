// Import required libraries
const functions = require("firebase-functions/v1");
const admin = require("firebase-admin");

// Initialize Firebase Admin (optional for some triggers)
admin.initializeApp();

exports.monitorNotifications = functions.firestore
    .document("notifications/{bookingId}")
    .onWrite(async (change, context) => {
      const bookingId = context.params.bookingId;

      const notificationsCollectionRef =
        admin.firestore().collection("notifications");
      const notificationRef = notificationsCollectionRef.doc(bookingId);

      const notificationsCollectionSnapshot =
        await notificationsCollectionRef.get();

      // Get notification data using a single get operation
      const notificationSnapshot = await notificationRef.get();
      const notificationData = notificationSnapshot.data();

      if (!notificationData) {
        return null; // No notification data, exit function
      }

      const {
        fcmTokens,
        title,
        body,
      } = notificationData; // Destructure data

      const previousRecordCount =
        change.before.exists ?
        notificationsCollectionSnapshot.size - 1 : 0;
      const currentRecordCount =
        change.after.exists ?
        notificationsCollectionSnapshot.size : 0;

      if (currentRecordCount > previousRecordCount) {
        // Send notification to each FCM token
        const sendNotification = async (fcmToken) => {
          const payload = {
            notification: {
              title, // Use destructured values
              body,
            },
          };

          await admin.messaging().sendToDevice(fcmToken, payload);
        };

        await Promise.all(fcmTokens.map(sendNotification));
      }
      return null;
    },
    );
