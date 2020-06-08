'''
@Time: 2019/12/18 15:49
@Author: mih
@Des:
'''
import torch
import torch.nn as nn

class LineNetwork(nn.Module):
    def __init__(self, feature_dim, hidden_layer_dim, output_dim):
        super(LineNetwork, self).__init__()

        self.line1 = nn.Sequential(
            nn.Linear(feature_dim, hidden_layer_dim, bias=True),
            nn.Dropout(p=0.3),
            nn.ReLU()
        )
        self.line2 = nn.Sequential(
            nn.Linear(hidden_layer_dim, hidden_layer_dim, bias=True),
            nn.Dropout(p=0.2),
            nn.ReLU()
        )


        self.line3 = nn.Sequential(
            nn.Linear(hidden_layer_dim, output_dim, bias=False),
            nn.Dropout(p=0.2),
            nn.ReLU()
        )

    def forward(self, input):
        out = self.line1(input)
        out = self.line2(out)
        out = self.line3(out)
        return out







