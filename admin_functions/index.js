// Import required libraries
const functions = require("firebase-functions/v1");
const admin = require("firebase-admin");

// Initialize Firebase Admin (optional for some triggers)
admin.initializeApp();
exports.monitorBookings = functions.firestore
    .document("bookings/{bookingId}")
    .onWrite(async (change, context) => {
      const bookingId = context.params.bookingId;

      const bookingsRef = admin.firestore().collection("bookings");
      const bookingsCollectionSnap = await bookingsRef.get();

      const bookingDocRef = bookingsRef.doc(bookingId);

      const bookingDocSnapshot = await bookingDocRef.get();
      const bookingData = bookingDocSnapshot.data();

      const fcmTokens = [];

      const adminsCollectionRef = admin.firestore().collection("admins");
      const adminsCollectionSnapshot = await adminsCollectionRef.get();

      adminsCollectionSnapshot.forEach((adminDoc) => {
        const adminData = adminDoc.data();
        // Getting the fcmTokens field, defaulting to empty array if not present
        const adminTokens = adminData.fcmTokens || [];
        adminTokens.forEach((token)=> {
          fcmTokens.push(token);
        });
      });

      console.log(fcmTokens);

      const userName = bookingData.userName;
      const vehicleName = bookingData.vehicleName;

      const previousRecordCount =
        change.before.exists ? bookingsCollectionSnap.size - 1 : 0;
      const currentRecordCount =
        change.after.exists ? bookingsCollectionSnap.size : 0;

      if (currentRecordCount > previousRecordCount) {
        const sendNotification = async (fcmToken) => {
          const payload = {
            notification: {
              title: "New Booking",
              body: `${userName} has booked ${vehicleName}.`,
            },
          };

          await admin.messaging().sendToDevice(fcmToken, payload);
        };

        await Promise.all(fcmTokens.map(sendNotification));
      }

      return null;
    });
