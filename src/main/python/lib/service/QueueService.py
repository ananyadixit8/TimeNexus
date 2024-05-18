import requests
from src.main.python.lib.helper import QUEUE_URL


class QueueService:

    def __init__(self, svc_url=None):
        self.svc_url = QUEUE_URL

    def insertMeetingInExecutionQueue(self, meeting_id):

        request_url = self.svc_url + f"/insertInMeetingQueue/{meeting_id}"

        try:
            response = requests.post(
                request_url
            )
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Error in inserting the meeting to Execution Queue: {e}")
