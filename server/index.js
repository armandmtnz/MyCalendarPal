const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// Root endpoint
app.get('/', (req, res) => {
  res.send('MCP Notification Server is running');
});

// POST /notify endpoint to trigger notifications
app.post('/notify', (req, res) => {
  const { title, message, userId } = req.body;

  if (!title || !message) {
    return res.status(400).json({ error: 'title and message are required' });
  }

  // Placeholder logic to trigger a notification
  // In a real application, you would integrate with a push notification service (e.g., FCM or APNS)
  console.log(`Sending notification to userId=${userId || 'all'}: "${title}" - "${message}"`);

  return res.json({ status: 'Notification triggered', payload: { title, message, userId } });
});

// Start server
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
