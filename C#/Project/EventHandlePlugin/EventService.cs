using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EventHandlePlugin
{
    class EventService : IEventService
    {
        List<Event> registedEventList = new List<Event>();

        public void registEvent(Event e)
        {
            registedEventList.Add(e);
        }

        public void removeEvent(Event e)
        {
            registedEventList.Remove(e);
        }
    }
}
