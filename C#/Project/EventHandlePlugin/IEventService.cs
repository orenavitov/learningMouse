using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EventHandlePlugin
{
    public interface IEventService
    {
        void registEvent(Event e);

        void removeEvent(Event e);
    }
}
