using OSGi.NET.Core;
using OSGi.NET.Core.Root;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Project
{
    public partial class Form1 : Form
    {

        IList<IBundle> bundles;

        public void initFramework()
        {
            var frameworkFactory = new FrameworkFactory();
            var framework = frameworkFactory.CreateFramework();

            framework.Init();
            framework.Start();

            bundles = framework.GetBundleContext().GetBundles();

        }

        public Form1()
        {
            InitializeComponent();

            initFramework();
        }

        private void menuStrip1_ItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {
            foreach (var bundle in bundles)
            {
                string bundleName = bundle.GetBundleAssemblyFileName();
                ToolStripMenuItem toolStripMenuItem = new ToolStripMenuItem(bundleName);
                this.pluginToolStripMenuItem.DropDownItems.Add(toolStripMenuItem);
            }
        }

        private void contextMenuStrip1_Opening(object sender, CancelEventArgs e)
        {
            foreach(var bundle in bundles)
            {
                string bundleName = bundle.GetBundleAssemblyFileName();
                ToolStripMenuItem toolStripMenuItem = new ToolStripMenuItem(bundleName);
                this.pluginToolStripMenuItem.DropDownItems.Add(toolStripMenuItem);
            }
            
            
        }
    }
}
